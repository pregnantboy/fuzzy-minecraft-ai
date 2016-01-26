//
//  RuleNumTableViewCell.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 25/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "RuleNumTableViewCell.h"

@implementation RuleNumTableViewCell

- (void)drawRect:(NSRect)dirtyRect {
    [super drawRect:dirtyRect];
    
    // Drawing code here.
}

- (void)setNum:(NSInteger)num {
    NSString* numString = [NSString stringWithFormat:@"%ld", (long)num];
    NSFont *txtFont = [NSFont fontWithName:@"DK Prince Frog" size:22];
    NSColor *txtColor = [NSColor whiteColor];
    NSMutableParagraphStyle *centredStyle = [[NSParagraphStyle defaultParagraphStyle] mutableCopy];
    [centredStyle setAlignment:NSCenterTextAlignment];
    NSDictionary *txtDict = @{
                              NSFontAttributeName: txtFont,
                              NSForegroundColorAttributeName: txtColor,
                              NSParagraphStyleAttributeName: centredStyle
                              };
    NSAttributedString *attrStr = [[NSAttributedString alloc] initWithString:numString attributes:txtDict];
    [self.number setAttributedStringValue:attrStr];
}

- (NSInteger)getNum {
    return [self.number integerValue];
}

@end
