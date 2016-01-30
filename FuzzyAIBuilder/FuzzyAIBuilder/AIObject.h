//
//  AIObject.h
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 29/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Rule.h"

@interface AIObject : NSObject <NSCoding>
- (instancetype)initWithAIName:(NSString *)AIName;
- (void)saveNewRule:(Rule*)newRule;
- (Rule *)getRuleAtIndex:(NSInteger) index;
- (void)replaceExistingRule:(NSInteger) index withRule: (Rule*)modifiedRule;
- (void)deleteRuleAtIndex:(NSInteger) index;
- (BOOL)moveUpRule:(NSInteger)index;
- (BOOL)moveDownRule:(NSInteger)index;
- (NSMutableArray *)ruleList;
- (NSInteger)getNumRules;
- (NSString *)getName;

- (instancetype)initWithCoder:(NSCoder *)aDecoder;
- (void)encodeWithCoder:(NSCoder *)aCoder;

@end
