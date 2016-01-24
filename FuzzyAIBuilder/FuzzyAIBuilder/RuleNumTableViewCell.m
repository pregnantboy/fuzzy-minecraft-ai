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

- (void)setNum:(NSNumber *)num {
    NSString* numString = [NSString stringWithFormat:@"%ld", (long)[num integerValue]];
    NSFont *txtFont = [NSFont fontWithName:@"DK Prince Frog" size:12];
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

- (NSNumber *)getNum {
    return [NSNumber numberWithInt:(int)[[self.number stringValue] integerValue]];
}

@end
