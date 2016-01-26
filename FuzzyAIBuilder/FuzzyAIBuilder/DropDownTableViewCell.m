//
//  DropDownTableViewCell.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 25/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "DropDownTableViewCell.h"
#define fontSize 26

@interface DropDownTableViewCell() {
    NSInteger type;
}

@end

@implementation DropDownTableViewCell

- (void)drawRect:(NSRect)dirtyRect {
    [super drawRect:dirtyRect];
    [self.popUp setTarget:self];
    [self.popUp.cell setArrowPosition:NSPopUpNoArrow];
    [self.popUp setAction:@selector(onValueChanged:)];
    // Drawing code here.
}

- (void)setType:(NSInteger)column {
    NSArray *conditions = [RuleCondition getPossibleConditions];
    switch (column) {
        case 2:
        {   type = 2;
            NSMenu *menu;
            menu = [[NSMenu alloc] init];
            NSMenuItem *placeholderItem = [[NSMenuItem alloc] initWithTitle:@"Selection Condition" action:nil keyEquivalent:@""];
            [placeholderItem setAttributedTitle:[DropDownTableViewCell changeToWhiteText:@"Selection Condition" withSize:fontSize]];
            [placeholderItem setHidden:YES];
            [menu addItem:placeholderItem];
            
            for (NSString *cond in conditions) {
                NSMenuItem *item = [[NSMenuItem alloc] initWithTitle:cond action:nil keyEquivalent:@""];
                [item setAttributedTitle:[DropDownTableViewCell changeToWhiteText:cond withSize:fontSize]];
                [menu addItem:item];
            }
            [self.popUp setMenu:menu];
            [self.popUp selectItemWithTitle:@"Selection Condition"];
        }
            break;
        case 4:
        default:
        {   type = 4;
            [self disableValueButton];
        }
            break;
    }
}



- (NSInteger)type {
    return type;
}

- (void)setString:(NSString *)string {
    [self.popUp selectItemWithTitle:string];
}

- (NSString *)getString {
    return [self.popUp selectedItem].title;
}

- (NSInteger)getRow {
    return self.popUp.tag;
}

- (void)disableValueButton {
    NSMenu *menu = [[NSMenu alloc] init];
    NSMenuItem *placeholderItem = [[NSMenuItem alloc] initWithTitle:@"Select Value" action:nil keyEquivalent:@""];
    [placeholderItem setAttributedTitle:[DropDownTableViewCell changeToWhiteText:@"Select Value" withSize:fontSize]];
    [placeholderItem setHidden:YES];
    [menu addItem:placeholderItem];
    [self.popUp setEnabled:NO];
    [self.popUp setMenu:menu];
    [self.popUp selectItemWithTitle:@"Select Value"];
}

- (void)enableValueButtonForCond:(NSString *)cond {
    NSMenu *menu = [[NSMenu alloc] init];
    NSMenuItem *placeholderItem = [[NSMenuItem alloc] initWithTitle:@"Select Value" action:nil keyEquivalent:@""];
    [placeholderItem setAttributedTitle:[DropDownTableViewCell changeToWhiteText:@"Select Value" withSize:fontSize]];
    [placeholderItem setHidden:YES];
    [menu addItem:placeholderItem];
    NSArray *values = [RuleCondition getPossibleValueForCond:cond];
    for (NSString *val in values) {
        NSMenuItem *item = [[NSMenuItem alloc] initWithTitle:val action:nil keyEquivalent:@""];
        [item setAttributedTitle:[DropDownTableViewCell changeToWhiteText:val withSize:fontSize]];
        [menu addItem:item];
    }
    [self.popUp setEnabled:YES];
    [self.popUp setMenu:menu];
    [self.popUp selectItemWithTitle:@"Select Value"];
}

- (IBAction)onValueChanged:(id)sender {
    [[NSNotificationCenter defaultCenter] postNotificationName:@"ConditionChanged" object:self];
}

+ (NSAttributedString *)changeToWhiteText:(NSString *)text withSize:(int)size {
    NSFont *txtFont = [NSFont fontWithName:@"DK Prince Frog" size:size];
    NSColor *txtColor = [NSColor colorWithCalibratedRed:254/255.0 green:254/255.0 blue:254/255.0 alpha:0.99];
    NSMutableParagraphStyle *centredStyle = [[NSParagraphStyle defaultParagraphStyle] mutableCopy];
    [centredStyle setAlignment:NSCenterTextAlignment];
    NSDictionary *txtDict = @{
                              NSStrokeColorAttributeName: [NSColor blackColor],
                              NSStrokeWidthAttributeName: @-2.0,
                              NSFontAttributeName: txtFont,
                              NSForegroundColorAttributeName: txtColor,
                              NSParagraphStyleAttributeName: centredStyle
                              };
    NSAttributedString *attrStr = [[NSAttributedString alloc] initWithString:text attributes:txtDict];
    return attrStr;
}
     
+ (NSAttributedString *)changeToBlackText:(NSString *)text withSize:(int)size {
    NSFont *txtFont = [NSFont fontWithName:@"DK Prince Frog" size:size];
    NSColor *txtColor = [NSColor blackColor];
    NSMutableParagraphStyle *centredStyle = [[NSParagraphStyle defaultParagraphStyle] mutableCopy];
    [centredStyle setAlignment:NSCenterTextAlignment];
    NSDictionary *txtDict = @{
                              NSFontAttributeName: txtFont,
                              NSForegroundColorAttributeName: txtColor,
                              NSParagraphStyleAttributeName: centredStyle
                              };
    NSAttributedString *attrStr = [[NSAttributedString alloc] initWithString:text attributes:txtDict];
    return attrStr;
}

@end
